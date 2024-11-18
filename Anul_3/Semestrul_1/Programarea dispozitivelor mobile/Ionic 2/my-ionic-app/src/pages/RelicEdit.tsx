import React, { useCallback, useContext, useEffect, useState } from 'react';
import {
    IonButton,
    IonContent,
    IonHeader,
    IonInput,
    IonLabel,
    IonLoading,
    IonPage,
    IonCheckbox,
} from '@ionic/react';
import { getLogger } from '../core';
import { RelicContext } from './RelicProvider';
import { RouteComponentProps } from 'react-router';
import { RelicProps } from './RelicProps';
import { format } from 'date-fns';
import CustomToolbar from '../components/CustomToolbar';
import { useNetwork } from '../use/useNetwork';

const log = getLogger('RelicEdit');

interface RelicEditProps extends RouteComponentProps<{ id?: string }> { }


function parseDDMMYYYY(dateString: string) {
    const [day, month, year] = dateString.split('/').map(Number);

    // Validate the components
    if (isNaN(day) || isNaN(month) || isNaN(year)) {
        log('Invalid date components');
        return null;
    }

    // Months are zero-based, so subtract 1 from the month
    const adjustedMonth = month - 1;

    // Create the Date object
    const parsedDate = new Date(year, adjustedMonth, day);

    if (
        parsedDate.getDate() !== day ||
        parsedDate.getMonth() !== adjustedMonth ||
        parsedDate.getFullYear() !== year
    ) {
        console.error('Invalid date');
        return null;
    }

    // Validate the Date object
    if (isNaN(parsedDate.getTime())) {
        log('Invalid date');
        return null;
    }

    return parsedDate;
}

const RelicEdit: React.FC<RelicEditProps> = ({ history, match }) => {
    const { relics, saving, saveRelic } = useContext(RelicContext);
    const [relic, setRelic] = useState<RelicProps | undefined>(undefined);
    const [name, setName] = useState('Type a name');
    const [location, setLocation] = useState('Type an location');
    const [dateInStock, setDateInStock] = useState<Date | undefined>(undefined);
    const [isCursed, setIsCursed] = useState(false);
    const [price, setPrice] = useState(0);

    useEffect(() => {
        log('useEffect - Fetching relic details');
        const routeId = match.params.id || '';
        const foundRelic = relics?.find((relic) => relic.id === routeId);
        setRelic(foundRelic);

        if (foundRelic) {
            log('useEffect - Setting relic details');
            setName(foundRelic.name);
            setLocation(foundRelic.location);
            setDateInStock(foundRelic.dateInStock);
            setIsCursed(foundRelic.isCursed);
            setPrice(foundRelic.price);
        }
    }, [match.params.id, relics]);

    const handleSave = useCallback(() => {
        const editedRelic: RelicProps = {
            id: relic?.id,
            name,
            location,
            dateInStock: dateInStock || new Date(),
            isCursed,
            price,
        };

        log('handleSave - Saving edited relic');
        saveRelic && saveRelic(editedRelic).then(() => {
            log('handleSave - Relic saved successfully. Navigating back.');
            history.goBack();
        });
    }, [relic, saveRelic, name, location, dateInStock, isCursed, price, history]);

    log('render ' + name);
    return (
        <IonPage >
            <IonHeader>
                <CustomToolbar title="Edit Relic" titleStyle="title" />
            </IonHeader>
            <IonContent>
                <div className='inputContainer' >
                    <IonLabel className='label'>Name:</IonLabel>
                    <IonInput className='input' value={name} onIonChange={(e) => setName(e.detail.value || '')} />
                </div>

                <div className='inputContainer'>
                    <IonLabel className='label'>Location:</IonLabel>
                    <IonInput className='input' value={location} onIonChange={(e) => setLocation(e.detail.value || '')} />
                </div>

                <div className='inputContainer'>
                    <IonLabel className='label'>Date In Stock:</IonLabel>
                    <IonInput
                        class="input"
                        className='input'
                        value={dateInStock ? format(new Date(dateInStock), 'dd/MM/yyyy') : ''}
                        onIonChange={(e) => {
                            const inputDate = parseDDMMYYYY(e.detail.value || '');
                            if (inputDate !== null) {
                                setDateInStock(inputDate);
                            }
                            else {
                                // If the date is invalid, change the input value to the previous valid date
                                e.detail.value = dateInStock ? format(new Date(dateInStock), 'dd/MM/yyyy') : '';
                            }
                        }}
                    />
                </div>

                <div className='inputContainer'>
                    <IonLabel className='label'>Cursed:</IonLabel>
                    <IonCheckbox className='checkbox' checked={isCursed} onIonChange={(e) => setIsCursed(e.detail.checked)} />
                </div>

                <div className='inputContainer'>
                    <IonLabel className='label'>Price:</IonLabel>
                    <IonInput className='input' type="number" value={price.toString()} onIonChange={(e) => setPrice(parseInt(e.detail.value || '0'))} />
                </div>

                <IonButton className="custom-button"
                           shape='round'
                           color='secondary'
                           style={{ marginTop: '20px' }}
                           onClick={handleSave}>
                    Save
                </IonButton>


                <IonLoading isOpen={saving} />
            </IonContent>
        </IonPage >
    );
};

export default RelicEdit;
