// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const DEVICE_MERCHANT_API = 'http://localhost:8006/merchant-device';
const TRANSACTION_DROP_API = 'http://localhost:8003/drop-transaction';

export const environment = {
  production: false,
  device_merchnat_api: DEVICE_MERCHANT_API,
  drop_transaction_api: TRANSACTION_DROP_API,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
