import React, { memo } from 'react';
import { IonItem, IonLabel, IonImg } from '@ionic/react';
import { format } from 'date-fns'; // Import the format function
import { getLogger } from '../core';
import { RelicProps } from './RelicProps';

const log = getLogger('Relic');

interface RelicPropsExt extends RelicProps {
    onEdit: (relicId?: string) => void;
}

const Relic: React.FC<RelicPropsExt> = ({ id, name, location, dateInStock, isCursed, price, photo, onEdit }) => {
    // Format the date using date-fns
    const formattedDate = dateInStock ? format(new Date(dateInStock), 'dd/MM/yyyy') : '';
    const webviewPath = `data:image/jpeg;base64,${photo}`;
    log('render book ' + id);
    return (
        <IonItem className="relic-item" onClick={() => onEdit(id)}>
            <IonLabel>
                <h2>{name}</h2>
                <p>{`Location: ${location}`}</p>
                <p>{`Date In Stock: ${formattedDate}`}</p>
                <p>{`Cursed: ${isCursed ? 'Yes' : 'No'}`}</p>
                <p>{`Price: ${price}`}</p>
            </IonLabel>
            {photo && (<IonImg
                src={webviewPath}
                alt={`${id}.jpeg`}
                style={{ width: '50%', height: 'auto' }}
            />)}
        </IonItem>
    );
};

export default memo(Relic);
