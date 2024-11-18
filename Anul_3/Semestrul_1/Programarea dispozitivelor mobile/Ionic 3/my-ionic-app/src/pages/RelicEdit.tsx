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
    IonFab,
    IonFabButton,
    IonFabList,
    IonIcon,
    IonImg,
    IonActionSheet,
} from '@ionic/react';
import { Prompt } from 'react-router-dom';
import { getLogger } from '../core';
import { RelicContext } from '../providers';
import { RouteComponentProps } from 'react-router';
import { RelicProps } from '../core/RelicProps';
import { format, set } from 'date-fns';
import CustomToolbar from '../components/CustomToolbar';
import { camera, images, trash, close } from 'ionicons/icons';
import { MyPhoto, usePhotos } from '../hooks/usePhotos';

const log = getLogger('RelicEdit');

interface RelicEditProps extends RouteComponentProps<{ id?: string }> { }


function parseDDMMYYYY(dateString: string) {
    const [day, month, year] = dateString.split('/').map(Number);

    if (isNaN(day) || isNaN(month) || isNaN(year)) {
        log('Invalid date components');
        return null;
    }

    const adjustedMonth = month - 1;

    const parsedDate = new Date(year, adjustedMonth, day);

    if (
        parsedDate.getDate() !== day ||
        parsedDate.getMonth() !== adjustedMonth ||
        parsedDate.getFullYear() !== year
    ) {
        console.error('Invalid date');
        return null;
    }

    if (isNaN(parsedDate.getTime())) {
        log('Invalid date');
        return null;
    }

    return parsedDate;
}

const RelicEdit: React.FC<RelicEditProps> = ({ history, match }) => {
    const { relics, saving, saveRelic } = useContext(RelicContext);
    const { takePhoto, deletePhoto } = usePhotos();
    const [relic, setRelic] = useState<RelicProps | undefined>(undefined);
    const [name, setName] = useState('Type a name');
    const [location, setLocation] = useState('Type an location');
    const [dateInStock, setDateInStock] = useState<Date | undefined>(undefined);
    const [isCursed, setIsCursed] = useState(false);
    const [price, setPrice] = useState(0);
    const [photo, setPhoto] = useState<string | undefined>(undefined);
    const [photoToDelete, setPhotoToDelete] = useState<MyPhoto>();
    const [unsavedChanges, setUnsavedChanges] = useState(false);

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
            setPhoto(foundRelic.photo);
        }
    }, [match.params.id, relics]);

    const myPhoto: MyPhoto | undefined = photo ? {
        filepath: `${relic?.id}.jpeg`,
        webviewPath: `data:image/jpeg;base64,${photo}`
    } : undefined;

    const handleSave = useCallback(() => {
        const editedRelic: RelicProps = {
            id: relic?.id,
            name,
            location,
            dateInStock: dateInStock || new Date(),
            isCursed,
            price,
            photo
        };

        setUnsavedChanges(false);
        log('handleSave - Saving edited relic');
        saveRelic && saveRelic(editedRelic).then(() => {
            log('handleSave - Relic saved successfully. Navigating back.');
            history.goBack();
        });
    }, [relic, saveRelic, name, location, dateInStock, isCursed, price, photo, history]);

    log('render ' + name);
    return (
        <IonPage>
            <IonHeader>
                <CustomToolbar title="Edit Relic" titleStyle="title" />
            </IonHeader>
            <IonContent>
                <div className='inputContainer' >
                    <IonLabel className='label'>Name:</IonLabel>
                    <IonInput className='input' value={name} onIonChange={(e) => {setName(e.detail.value || ''); setUnsavedChanges(true);}} />
                </div>

                <div className='inputContainer'>
                    <IonLabel className='label'>Location:</IonLabel>
                    <IonInput className='input' value={location} onIonChange={(e) => {setLocation(e.detail.value || '');  setUnsavedChanges(true);}} />
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
                                setUnsavedChanges(true);
                            }
                            else {
                               e.detail.value = dateInStock ? format(new Date(dateInStock), 'dd/MM/yyyy') : '';
                            }
                        }}
                    />
                </div>

                <div className='inputContainer'>
                    <IonLabel className='label'>Cursed:</IonLabel>
                    <IonCheckbox className='checkbox' checked={isCursed} onIonChange={(e) => {setIsCursed(e.detail.checked); setUnsavedChanges(true);}} />
                </div>

                <div className='inputContainer'>
                    <IonLabel className='label'>Price:</IonLabel>
                    <IonInput className='input' type="number" value={price.toString()} onIonChange={(e) => {setPrice(parseInt(e.detail.value || '0')); setUnsavedChanges(true);}} />
                </div>

                {myPhoto && (
                    <IonImg
                        onClick={() => setPhotoToDelete(myPhoto)}
                        src={myPhoto.webviewPath}
                        alt={myPhoto.filepath}
                        style={{ width: '100%', height: 'auto' }}
                    />
                )}

                <IonButton className="custom-button"
                           shape='round'
                           color='secondary'
                           style={{ marginTop: '20px' }}
                           onClick={() => {
                               setUnsavedChanges(false);
                               handleSave();
                           }}>
                    Save
                </IonButton>

                <IonFab vertical="bottom" horizontal="end" slot="fixed">
                    <IonFabButton>
                        <IonIcon icon={images} title="Load Photo" aria-label="Load Photo" />
                    </IonFabButton>
                    <IonFabList side="top">
                        <IonFabButton
                            onClick={async () => {
                                const newPhoto = await takePhoto();
                                setPhoto(newPhoto);
                                setUnsavedChanges(true);
                            }}
                        >
                            <IonIcon icon={camera} title="Take Photo" aria-label="Take Photo" />
                        </IonFabButton>
                    </IonFabList>
                </IonFab>
                <IonActionSheet
                    isOpen={!!photoToDelete}
                    buttons={[{
                        text: 'Delete',
                        role: 'destructive',
                        icon: trash,
                        handler: async () => {
                            if (photoToDelete) {
                                deletePhoto(photoToDelete.filepath);
                                setPhoto(undefined);
                                setPhotoToDelete(undefined);
                            }
                        }
                    }, {
                        text: 'Cancel',
                        icon: close,
                        role: 'cancel'
                    }]}
                    onDidDismiss={() => setPhotoToDelete(undefined)}
                />
                <Prompt
                    when={unsavedChanges}
                    message="You have unsaved changes. Are you sure you want to leave?"
                />


                <IonLoading isOpen={saving} />
            </IonContent>
        </IonPage >
    );
};

export default RelicEdit;
