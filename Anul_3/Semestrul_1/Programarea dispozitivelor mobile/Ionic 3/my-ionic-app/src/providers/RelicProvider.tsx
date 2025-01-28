import React, { useCallback, useContext, useEffect, useReducer, useRef } from 'react';
import PropTypes from 'prop-types';
import { getLogger } from '../core';
import { RelicProps } from '../core/RelicProps';
import { createRelic, getRelics, newWebSocket, updateRelic } from '../api/relicApi';
import { AuthContext } from '.';
import { useNetwork } from '../hooks/useNetwork';
import { Preferences } from '@capacitor/preferences';
import { usePhotos } from '../hooks/usePhotos';

const log = getLogger('RelicProvider');

type SaveRelicFn = (relic: RelicProps) => Promise<any>;

export interface RelicsState {
    relics?: RelicProps[],
    fetching: boolean,
    fetchingError?: Error | null,
    saving: boolean,
    savingError?: Error | null,
    saveRelic?: SaveRelicFn,
}

interface ActionProps {
    type: string,
    payload?: any,
}

const initialState: RelicsState = {
    fetching: false,
    saving: false,
};

const FETCH_RELICS_STARTED = 'FETCH_RELICS_STARTED';
const FETCH_RELICS_SUCCEEDED = 'FETCH_RELICS_SUCCEEDED';
const FETCH_RELICS_FAILED = 'FETCH_RELICS_FAILED';
const SAVE_RELIC_STARTED = 'SAVE_RELIC_STARTED';
const SAVE_RELIC_SUCCEEDED = 'SAVE_RELIC_SUCCEEDED';
const SAVE_RELIC_FAILED = 'SAVE_RELIC_FAILED';

const reducer: (state: RelicsState, action: ActionProps) => RelicsState =
    (state, { type, payload }) => {
        switch (type) {
            case FETCH_RELICS_STARTED:
                return { ...state, fetching: true, fetchingError: null };
            case FETCH_RELICS_SUCCEEDED:
                log('payload.relics in FETCH_RELICS_SUCCEEDED:', payload.relics);
                return { ...state, relics: payload.relics, fetching: false };
            case FETCH_RELICS_FAILED:
                return { ...state, fetchingError: payload.error, fetching: false };
            case SAVE_RELIC_STARTED:
                return { ...state, savingError: null, saving: true };
            case SAVE_RELIC_SUCCEEDED:
                const relics = [...(state.relics || [])];
                const relic = payload.relic;
                const index = relics.findIndex(b => b.id === relic.id);
                if (index === -1) {
                    relics.splice(relics.length, 0, relic);
                } else {
                    relics[index] = relic;
                }
                log('relics in SAVE_RELIC_SUCCEEDED:', relics);
                return { ...state, relics, savingEroor: null, saving: false };
            case SAVE_RELIC_FAILED:
                return { ...state, savingError: payload.error, saving: false };
            default:
                return state;
        }
    };

export const RelicContext = React.createContext<RelicsState>(initialState);

interface RelicProviderProps {
    children: PropTypes.ReactNodeLike,
}

export const RelicProvider: React.FC<RelicProviderProps> = ({ children }) => {
    const { token } = useContext(AuthContext);
    const [state, dispatch] = useReducer(reducer, initialState);
    const { relics, fetching, fetchingError, saving, savingError } = state;
    const { networkStatus } = useNetwork();
    const { savePhotoLocally, deletePhoto } = usePhotos();
    useEffect(getRelicsEffect, [token]);
    useEffect(webSocketEffect, [token, relics]);
    const saveRelic = useCallback<SaveRelicFn>(saveRelicCallback, [token, networkStatus]);
    const value = { relics, fetching, fetchingError, saving, savingError, saveRelic };

    useEffect(() => {
        if (networkStatus.connected) {
            syncRelics();
        }
    }, [networkStatus.connected]);

    log('returns');
    return (
        <RelicContext.Provider value={value}>
            {children}
        </RelicContext.Provider>
    );

    function syncRelics() {
        log('syncRelics - networkStatus.connected', networkStatus.connected);
        log('syncRelics - networkStatus.shouldSync', networkStatus.shouldSync);
        if (networkStatus.connected && networkStatus.shouldSync === 'disconnected') {
            syncRelicsCallback();
        }

        async function syncRelicsCallback() {
            // get the relics from the local storage Preferences
            const localRelics = await Preferences.get({ key: 'relics' });
            log('localRelics:', localRelics);

            if (localRelics.value) {
                let relicsArray: RelicProps[] = JSON.parse(localRelics.value);
                const hasDirtyRelics = relicsArray.some((relic) => relic?.dirty?.valueOf() === true);
                if (hasDirtyRelics) {
                    // iterate through the relics and save them on the server
                    for (let relic of relicsArray) {
                        try {
                            if (relic?.dirty?.valueOf() === true) {
                                relic = { ...relic, dirty: false };
                                const savedRelic = await saveRelic(relic);
                                if (relic.id && savedRelic.id) {
                                    if (parseFloat(relic.id) < 0) {
                                        relicsArray = relicsArray.filter(b => b.id !== relic.id);
                                        relicsArray.push(savedRelic);
                                    } else {
                                        const index = relicsArray.findIndex(b => b.id === relic.id);
                                        if (index !== -1) {
                                            relicsArray[index] = savedRelic;
                                        }
                                    }
                                }
                            }
                        } catch (error) {
                            log('Error syncing relic:', error);
                        }
                    }
                    Preferences.set({ key: 'relics', value: JSON.stringify(relicsArray) });
                    dispatch({ type: FETCH_RELICS_SUCCEEDED, payload: { relics: relicsArray } });
                }
            }
        }
    }


    function getRelicsEffect() {
        let canceled = false;
        if (token) {
            fetchRelics();
        }
        return () => {
            canceled = true;
        }


        async function fetchRelics() {
            try {
                log('Info> fetchRelics started!');
                dispatch({ type: FETCH_RELICS_STARTED });
                let relics = await getRelics(token);

                relics.forEach(relic => {
                    if (relic.photo !== null) {
                        savePhotoLocally(relic.id!, relic.photo!);
                    }
                });

                const dirty = false;
                relics = relics.map((relic: RelicProps) => {
                    return { ...relic, dirty: dirty };
                });
                await Preferences.set({ key: 'relics', value: JSON.stringify(relics) });

                // wait a bit
                await new Promise(resolve => setTimeout(resolve, 60));
                log('fetchRelics succeeded');
                if (!canceled) {
                    dispatch({ type: FETCH_RELICS_SUCCEEDED, payload: { relics } });
                }
            } catch (error) {
                log('fetchRelics failed');
                dispatch({ type: FETCH_RELICS_FAILED, payload: { error } });
            }
        }
    }

    async function saveRelicCallback(relic: RelicProps) {
        try {
            if (networkStatus.connected) {
                log('saveRelic started');
                dispatch({ type: SAVE_RELIC_STARTED });
                const savedRelic = await (relic.id ? updateRelic(token, relic) : createRelic(token, relic));
                if (relic.id && relic.photo !== null && relic.photo !== undefined && relic.photo !== '') {
                    savePhotoLocally(relic.id!, relic.photo!);
                }
                log('saveRelic succeeded');
                dispatch({ type: SAVE_RELIC_SUCCEEDED, payload: { relic: savedRelic } });
                return savedRelic;
            }
            else {
                log('saveRelic failed - > store the relic in the local storage');
                const relicId = relic.id ? relic.id : (-(Math.random() * 1000000)).toString();
                const relicToSave = { ...relic, id: relicId, dirty: true };
                const relics = await Preferences.get({ key: 'relics' });

                let relicsArray: RelicProps[] = [];
                if (relics.value) {
                    relicsArray = JSON.parse(relics.value);
                }
                const index = relicsArray.findIndex(b => b.id === relicToSave.id);
                if (index !== -1) {
                    relicsArray[index] = relicToSave;
                } else {
                    relicsArray.push(relicToSave);
                }
                await Preferences.set({ key: 'relics', value: JSON.stringify(relicsArray) });

                const error = { message: "The relic could not be save on the server right now, but it will be as soon as you are back online!" };
                dispatch({ type: SAVE_RELIC_FAILED, payload: { error } });
                dispatch({ type: SAVE_RELIC_SUCCEEDED, payload: { relic: relicToSave } });
            }
        } catch (error) {
            log('saveRelic failed');
            dispatch({ type: SAVE_RELIC_FAILED, payload: { error } });
        }
    }

    function webSocketEffect() {
        let canceled = false;
        log('wsEffect - connecting');
        let closeWebSocket: (() => void) | undefined;

        if (token?.trim()) {
            closeWebSocket = newWebSocket(token, async message => {
                if (canceled) {
                    return;
                }
                const { type, payload: relic } = message;
                log(`ws message, relic type ${type} and relic`, relic);
                if (type === 'created' || type === 'updated') {
                    if (relics) {
                        const relicsArray = [...relics];
                        const index = relicsArray.findIndex(b => b.id === relic.id);
                        if (index === -1) {
                            relicsArray.splice(relicsArray.length, 0, relic);
                        } else {
                            relicsArray[index] = relic;
                        }
                        Preferences.set({ key: 'relics', value: JSON.stringify(relicsArray) });
                        if (relic.photo === null) {
                            deletePhoto(`${relic.id}.jpeg`);
                        }

                    }
                    dispatch({ type: SAVE_RELIC_SUCCEEDED, payload: { relic } });
                }
            });
        }

        return () => {
            log('wsEffect - disconnecting');
            canceled = true;
            closeWebSocket?.(); // Check if closeWebSocket is defined before calling it
        }
    }

};
