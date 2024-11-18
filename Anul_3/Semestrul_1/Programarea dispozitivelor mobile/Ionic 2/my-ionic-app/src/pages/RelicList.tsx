import React, { useContext, useState, useEffect } from 'react';
import { RouteComponentProps } from 'react-router';
import {
    IonContent,
    IonFab,
    IonFabButton,
    IonHeader,
    IonIcon,
    IonList,
    IonLoading,
    IonPage,
    IonToast,
    IonInfiniteScroll,
    IonInfiniteScrollContent,
} from '@ionic/react';
import { add } from 'ionicons/icons';
import Relic from './Relic';
import { getLogger } from '../core';
import { RelicContext } from './RelicProvider';
import CustomToolbar from '../components/CustomToolbar';

const log = getLogger('RelicList');

const RelicList: React.FC<RouteComponentProps> = ({ history }) => {
    const { relics, fetching, fetchingError, savingError } = useContext(RelicContext);
    const [showToast, setShowToast] = useState(false);
    const [toastMessage, setToastMessage] = useState('');
    const handleToastClose = () => setShowToast(false);
    useEffect(() => {
        log('useEffect');
        if (savingError) {
            setToastMessage(savingError.message);
            setShowToast(true);
        }
    }, [savingError]);
    useEffect(() => {
        log('useEffect');
        if (fetchingError) {
            setToastMessage(fetchingError.message);
            setShowToast(true);
        }
    }, [fetchingError]);

    const [loadedRelics, setLoadedRelics] = useState(4);
    const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false);

    const loadMoreData = () => {
        const nextSetOfRelics = loadedRelics + 4;
        setLoadedRelics(nextSetOfRelics);
        setDisableInfiniteScroll(nextSetOfRelics >= relics?.length!);
    };

    useEffect(() => {
        setLoadedRelics(4);
    }, [relics]);

    log('render ', 'yes/no: ', fetching, ' ' + JSON.stringify(relics?.slice(0, loadedRelics)));

    return (
        <IonPage>
            <IonHeader>
                <CustomToolbar title="Relics - List" titleStyle="title" />
            </IonHeader>
            <IonContent>
                <IonLoading isOpen={fetching} message="Fetching relics" />
                {relics && (
                    <><IonList>
                        {relics.slice(0, loadedRelics).map(({ id, name, location, dateInStock, isCursed, price }) => (
                            <Relic
                                key={id}
                                id={id}
                                name={name}
                                location={location}
                                dateInStock={dateInStock}
                                isCursed={isCursed}
                                price={price}
                                onEdit={(relicId) => history.push(`/relic/${relicId}`)}
                            />
                        ))}
                    </IonList>
                        <IonInfiniteScroll
                            threshold="88px"
                            disabled={disableInfiniteScroll}
                            onIonInfinite={(e: CustomEvent<void>) => {
                                loadMoreData();
                                (e.target as HTMLIonInfiniteScrollElement).complete();
                            }}
                        >
                            <IonInfiniteScrollContent loadingText="Loading more data..."></IonInfiniteScrollContent>
                        </IonInfiniteScroll>
                    </>
                )}
                <IonToast
                    isOpen={showToast}
                    onDidDismiss={handleToastClose}
                    message={toastMessage}
                    duration={3000}
                    cssClass="custom-toast"
                    position="middle"
                />
                <IonFab vertical="bottom" horizontal="end" slot="fixed">
                    <IonFabButton onClick={() => history.push('/relic')}>
                        <IonIcon icon={add} title='add' aria-label='Add' />
                    </IonFabButton>
                </IonFab>
            </IonContent>
        </IonPage>
    );
};

export default RelicList;
