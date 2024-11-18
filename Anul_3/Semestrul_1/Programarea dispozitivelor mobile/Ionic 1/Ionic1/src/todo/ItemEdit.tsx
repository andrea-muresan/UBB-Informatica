import React, { useCallback, useContext, useEffect, useState } from 'react';
import {
  IonButton,
  IonButtons,
  IonCheckbox,
  IonContent,
  IonDatetime,
  IonHeader,
  IonInput,
  IonItemOption,
  IonLabel,
  IonLoading,
  IonPage,
  IonSelect,
  IonSelectOption,
  IonTitle,
  IonToolbar
} from '@ionic/react';
import { getLogger } from '../core';
import { ItemContext } from './ItemProvider';
import { RouteComponentProps } from 'react-router';
import { ItemProps } from './ItemProps';

const log = getLogger('ItemEdit');

interface ItemEditProps extends RouteComponentProps<{
  id?: string;
}> {}

const ItemEdit: React.FC<ItemEditProps> = ({ history, match }) => {
  const { items, saving, deleting, savingError, deletingError, saveItem, deleteItem2 } = useContext(ItemContext);
  const [name, setName] = useState('');
  const [dateInStock, setDateInStock] = useState<Date>(new Date(Date.now()));
  const [price, setPrice]=useState(0);
  const [isCursed, setIsCursed]=useState<boolean>(false);

  const [item, setItem] = useState<ItemProps>();
  useEffect(() => {
    log('useEffect');
    const routeId = match.params.id || '';
    const item = items?.find(it => it.id === routeId);
    setItem(item);
    if (item) {
      console.log(item);
      setName(item.name);
      setDateInStock(item.dateInStock)
      setPrice(item.price)
      setIsCursed(item.isCursed);
    }
  }, [match.params.id, items]);

  const handleSave = useCallback(() => {
    const editedItem = { ...item, name, dateInStock, price, isCursed };
    saveItem && saveItem(editedItem).then(() => history.goBack());
  }, [item, saveItem, name, dateInStock, price, isCursed, history]);

  const handleDelete = useCallback(() => {
    const editedItem = item;
    console.log(deleteItem2);
    console.log(editedItem);
    deleteItem2 && deleteItem2(editedItem).then(() => history.goBack());
  }, [item, deleteItem2, history]);

  log('render');
  console.log(item);
  return (
      <IonPage>
        <IonHeader>
          <IonToolbar>
            <IonTitle>Edit</IonTitle>
            <IonButtons slot="end">
              <IonButton onClick={handleSave}> Save </IonButton>
              <IonButton onClick={handleDelete}> Delete </IonButton>
            </IonButtons>
          </IonToolbar>
        </IonHeader>
        <IonContent>
          <br/>
          <IonLabel><b>Name</b></IonLabel>
          <IonInput value={name} onIonChange={e => setName(e.detail.value || '')} />
          <br/>
          <IonLabel><b>Date Acquired</b></IonLabel>
          <IonDatetime presentation="date" value={dateInStock.toString()} onIonChange={e=>{ setDateInStock(new Date(Date.parse(e.detail.value?.toString() || new Date(Date.now()).toString())))}}/>
          <br/>
          <IonLabel><b>Cursed</b></IonLabel>
          <IonCheckbox checked={isCursed} onIonChange={e =>{ setIsCursed(e.detail.checked) } } />

          <br/>
          <IonLabel><b>Price</b></IonLabel>
          <IonInput type="number" value={price} onIonChange={e => setPrice(Number.parseInt(e.detail.value || "0"))} />


          <IonLoading isOpen={saving} />
          {savingError && (
              <div>{savingError.message || 'Failed to save item'}</div>
          )}
          <IonLoading isOpen={deleting} />
          {deletingError && (
              <div>{deletingError.message || 'Failed to delete item'}</div>
          )}
        </IonContent>
      </IonPage>
  );
};

export default ItemEdit;
