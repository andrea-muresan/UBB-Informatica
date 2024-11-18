import React, { useState, useContext, useEffect } from 'react';
import {
    IonContent,
    IonHeader,
    IonList,
    IonPage,
    IonSearchbar,
    IonInfiniteScroll,
    IonInfiniteScrollContent
} from '@ionic/react';
import { getLogger } from '../core';
import { RelicContext } from '../providers/RelicProvider';
import CustomToolbar from '../components/CustomToolbar';
import Relic from '../core/Relic';
import { RouteComponentProps } from 'react-router';

const log = getLogger('RelicSearch');

const RelicSearch: React.FC<RouteComponentProps> = ({ history }) => {
    const { relics } = useContext(RelicContext);

    const [nameSearch, setNameSearch] = useState<string>('');
    const [locationSearch, setLocationSearch] = useState<string>('');

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

    log('render');

    return (
        <IonPage>
            <IonHeader>
                <CustomToolbar title="Search relics" titleStyle="title" />
            </IonHeader>
            <IonContent fullscreen>
                <IonSearchbar
                    value={nameSearch}
                    debounce={1000}
                    onIonChange={(e) => setNameSearch(e.detail.value!)}
                    placeholder="Search by name"
                    className="custom-searchbar"
                ></IonSearchbar>
                <IonSearchbar
                    value={locationSearch}
                    debounce={1000}
                    onIonChange={(e) => setLocationSearch(e.detail.value!)}
                    placeholder="Search by location"
                    className="custom-searchbar"
                ></IonSearchbar>
                <IonList>
                    {relics && relics
                        .filter((relic) => {
                            const nameMatch = relic.name.toLowerCase().includes(nameSearch.toLowerCase());
                            const locationMatch = relic.location.toLowerCase().includes(locationSearch.toLowerCase());
                            return nameMatch && locationMatch;
                        })
                        .slice(0, loadedRelics)
                        .map(({ id, name, location, dateInStock, isCursed, price }) => (
                            <Relic
                                key={id}
                                id={id}
                                name={name}
                                location={location}
                                dateInStock={dateInStock}
                                isCursed={isCursed}
                                price={price}
                                onEdit={id => history.push(`/relic/${id}`)}
                            />
                        ))
                    }
                </IonList>
                <IonInfiniteScroll
                    threshold="50px"
                    disabled={disableInfiniteScroll}
                    onIonInfinite={(e: CustomEvent<void>) => {
                        loadMoreData();
                        (e.target as HTMLIonInfiniteScrollElement).complete();
                    }}
                >
                    <IonInfiniteScrollContent loadingText="Loading more data..."></IonInfiniteScrollContent>
                </IonInfiniteScroll>
            </IonContent>
        </IonPage>
    );
};

export default RelicSearch;
