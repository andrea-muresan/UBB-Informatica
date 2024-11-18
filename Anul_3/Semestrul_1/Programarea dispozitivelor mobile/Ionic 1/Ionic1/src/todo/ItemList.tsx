import React, { useContext } from 'react';
import { RouteComponentProps } from 'react-router';
import {
  IonContent,
  IonFab,
  IonFabButton,
  IonHeader,
  IonIcon,
  IonList, IonLoading,
  IonPage,
  IonTitle,
  IonToolbar
} from '@ionic/react';
import { add } from 'ionicons/icons';
import Item from './Item';
import { getLogger } from '../core';
import { ItemContext } from './ItemProvider';

const log = getLogger('ItemList');

const ItemList: React.FC<RouteComponentProps> = ({ history }) => {
  const { items, fetching, fetchingError } = useContext(ItemContext);
  log('render');
  return (
      <IonPage>
        <IonHeader>
          <IonToolbar>
            <IonTitle>Relics of the Forgotten Realm</IonTitle>
          </IonToolbar>
        </IonHeader>
        <IonContent>
          <IonLoading isOpen={fetching} message="Fetching items" />
          {items && (
              <IonList>
                {items.map(({ id, name, dateInStock, platform, price, isCursed }) =>
                    <Item key={id} id={id} name={name} onEdit={id => history.push(`/item/${id}`)}
                          dateInStock={dateInStock} platform={platform}
                          isCursed={isCursed}
                          price={price}/>)}
              </IonList>
          )}
          {fetchingError && (
              <div>{fetchingError.message || 'Failed to fetch items'}</div>
          )}
          <IonFab vertical="bottom" horizontal="end" slot="fixed">
            <IonFabButton onClick={() => history.push('/item')}>
              <IonIcon icon={add} />
            </IonFabButton>
          </IonFab>
        </IonContent>
      </IonPage>
  );
};

export default ItemList;
