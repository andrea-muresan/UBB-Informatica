import React, { useState, useContext, useEffect } from 'react';
import {
    IonContent,
    IonHeader,
    IonItem,
    IonLabel,
    IonList,
    IonSelect,
    IonSelectOption,
    IonPage,
    IonInfiniteScroll,
    IonInfiniteScrollContent,
    IonRange
} from '@ionic/react';
import { getLogger } from '../core';
import { RelicContext } from '../providers';
import CustomToolbar from '../components/CustomToolbar';
import Relic from '../core/Relic';
import { RouteComponentProps } from 'react-router';
import { RelicProps } from '../core/RelicProps';

const log = getLogger('RelicSearch');

const RelicFilter: React.FC<RouteComponentProps> = ({ history }) => {
    const { relics } = useContext(RelicContext);
    const [filteredRelics, setFilteredRelics] = useState<RelicProps[] | undefined>(undefined);
    const [loading, setLoading] = useState(true);

    log('entered');

    // Calculate the minimum and maximum prices from the existing relics
    const minPrice = relics ? Math.min(...(relics.map(relic => relic.price || 0))) : 0;
    const maxPrice = relics ? Math.max(...(relics.map(relic => relic.price || 0))) : 0;

    const [priceFilter, setPriceFilter] = useState<{ lower: number; upper: number }>({
        lower: minPrice,
        upper: maxPrice,
    });

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

    const [CursedFilter, setCursedFilter] = useState<boolean | undefined>(undefined);

    async function filterRelics() {
        const filtered = relics?.filter((relic) => {
            const priceMatch =
                (priceFilter.lower === undefined || relic.price >= priceFilter.lower) &&
                (priceFilter.upper === undefined || relic.price <= priceFilter.upper);

            const CursedMatch =
                CursedFilter === undefined ||
                (relic.isCursed && CursedFilter) ||
                (!relic.isCursed && !CursedFilter);

            return priceMatch && CursedMatch;
        });
        return filtered;
    }

    useEffect(() => {
        setLoading(true);
        filterRelics().then((result) => {
            setFilteredRelics(result);
            setLoading(false);
        });
    }, [priceFilter, CursedFilter]);

    log('render');

    return (
        <IonPage>
            <IonHeader>
                <CustomToolbar title="Filter relics" titleStyle="title" />
            </IonHeader>
            <IonContent fullscreen>
                <IonItem>
                    <IonSelect
                        value={CursedFilter}
                        placeholder="Select Cursed"
                        onIonChange={(e) => setCursedFilter(e.detail.value)}
                        aria-label="Select Cursed"
                        interface="alert"
                    >
                        <IonSelectOption value={true}>Cursed</IonSelectOption>
                        <IonSelectOption value={false}>Not Cursed</IonSelectOption>
                    </IonSelect>
                </IonItem>

                <IonItem>
                    <IonLabel>Filter by price:</IonLabel>
                    <IonRange
                        title='Dual Knobs Range'
                        pin={true}
                        aria-label='Dual Knobs Range'
                        dualKnobs={true}
                        value={priceFilter}
                        min={minPrice}
                        max={maxPrice}
                        onIonChange={(e) => setPriceFilter(e.detail.value as { lower: number; upper: number })}
                    ></IonRange>
                </IonItem>
                <IonItem>
                    <IonLabel>Lower Price: {priceFilter.lower}</IonLabel>
                    <IonLabel>Upper Price: {priceFilter.upper}</IonLabel>
                </IonItem>


                <IonList>
                    {!loading && filteredRelics && filteredRelics.slice(0, loadedRelics).map(({ id, name, location, dateInStock, isCursed, price }) => (
                        <Relic
                            key={id}
                            id={id}
                            name={name}
                            location={location}
                            dateInStock={dateInStock}
                            isCursed={isCursed}
                            price={price}
                            onEdit={() => history.push(`/relic/${id}`)}
                        />
                    ))}
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

export default RelicFilter;
