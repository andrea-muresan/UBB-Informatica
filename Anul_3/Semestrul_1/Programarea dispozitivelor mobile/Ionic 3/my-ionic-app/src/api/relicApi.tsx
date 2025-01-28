import axios from 'axios';
import { authConfig, baseUrl, getLogger, withLogs } from '../core';
import { RelicProps } from '../core/RelicProps';
const log = getLogger('restApi');
const relicUrl = `http://${baseUrl}/api/relic`;
export const getRelics: (token: string) => Promise<RelicProps[]> = token => {
    return withLogs(axios.get(relicUrl, authConfig(token)), 'getRelics');
}
export const createRelic: (token: string, Relic: RelicProps) => Promise<RelicProps> = (token, relic) => {
    return withLogs(axios.post(relicUrl, relic, authConfig(token)), 'createRelic');
}
export const updateRelic: (token: string, relic: RelicProps) => Promise<RelicProps> = (token, relic) => {
    return withLogs(axios.put(`${relicUrl}/${relic.id}`, relic, authConfig(token)), 'updateRelic');
}
interface MessageData {
    type: string;
    payload: RelicProps;
}
export const newWebSocket = (token: string, onMessage: (data: MessageData) => void) => {
    try {
        const ws = new WebSocket(`ws://${baseUrl}`)
        ws.onopen = () => {
            log('web socket onopen');
            ws.send(JSON.stringify({ type: 'authorization', payload: { token } }));
        };
        ws.onclose = () => {
            log('web socket onclose');
        };
        ws.onerror = error => {
            log('web socket onerror', error);
        };
        ws.onmessage = messageEvent => {
            log('web socket onmessage', messageEvent.data);
            onMessage(JSON.parse(messageEvent.data));
        };
        return () => {
            log('web socket onclose PROBLEM HERE MAYBE???');
            ws.close();
        }
    }
    catch (error) {
        log('We are offline, so no ws can be established: ', error);
    }
}
