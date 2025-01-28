// // useSyncRelics.ts
// import { useEffect, useCallback, useContext } from 'react';
// import { useNetwork } from './useNetwork';
// import { RelicContext } from '../pages/RelicProvider';
//
// const SYNC_INTERVAL = 10000; // Adjust the interval as needed
//
// const useSyncRelics = () => {
//   const { networkStatus } = useNetwork();
//   const { saveRelic, relics } = useContext(RelicContext); // Update with your actual context methods
//
//   const syncRelics = useCallback(async () => {
//     if (relics) {
//       if (networkStatus.connected && relics.length > 0) {
//         // If online and there are locally stored relics, sync them
//         for (const localRelic of relics) {
//           try {
//             if (saveRelic) {
//               await saveRelic(localRelic);
//             }
//           } catch (error) {
//             console.error('Error syncing relic:', error);
//             // Handle errors as needed
//           }
//         }
//       }
//     }
//   }, [networkStatus.connected, relics, saveRelic]);
//
//   useEffect(() => {
//     const syncInterval = setInterval(syncRelics, SYNC_INTERVAL);
//
//     return () => clearInterval(syncInterval);
//   }, [networkStatus]);
// };
//
// export default useSyncRelics;
