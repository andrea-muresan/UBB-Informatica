import React, { memo, version } from 'react';
import { IonItem, IonLabel, IonNote } from '@ionic/react';
import { getLogger } from '../core';
import { ItemProps } from './ItemProps';

const log = getLogger('Item');

interface ItemPropsExt extends ItemProps {
    onEdit: (id?: string) => void;
}

const Item: React.FC<ItemPropsExt> = ({ id, name, dateInStock, price , isCursed, onEdit }) => {
    return (
        <IonItem onClick={() => onEdit(id)}>
            <IonLabel>NAME: {name}</IonLabel>
            <IonLabel>IS CURSED: {isCursed ? "YES" : "NO"}</IonLabel>
            <IonLabel>PRICE: {price}</IonLabel>
            <IonLabel>DATE ACQUIRED: {new Date(dateInStock).toISOString().split("T")[0]}</IonLabel>
        </IonItem>
    );
};

export default memo(Item);
