# TODO PROJECT

### Integrity Checks Service.

  - [x]   create Transaction Model.
  - [x]   process validation : check for DuplicatedMessage.
  - [x]   process validation : check for MerchantNotFound(Drop).
  - [x]   process validation : check for MerchantNotMapped(Drop).
  - [x]   process validation : check for MessageFormat.
  - [x]   process validation : check for TransactionVerify(REMOVAL && VERIFICATION).

  - [x]   add Integrity Controller to get message Request.

  - [x]   Handle Exceptions.

  - [x]   Add Transaction repository.
  - [x]   Add Transaction Service and Implmentation.

  - [x]   Add Eureka client (Eurika server can know about service).
  - [x]   add Feign Configuration (comunicate between services).

  - [x]   add Proxy to communicate to other services.


### Drop Messages Service.

- [x] Get a list of all drop messages.
- [x] Find a drop message by id.
- [x] Find a drop message by merchant number (list).
- [x] Find a drop message by device number. (list).
- [x] Find a drop message by Bag Number. (list). // Transaction Verify.
- [x] Find drop message by merchant number/transactionId/bagNUmber/date.
- [x] Save drop message (comming from Integrity Service). 


### Device Merchant Service.

  - [x] create (Device - Merchant) model.
  - [x] add Dto for model.
  - [x] create Conroller - repository - services.
  - [x] add validation.

  ##### AVailable Operations.
  - [x] Add new Merchant.
  - [x] get List of Merchants.
  - [x] get Merchant By Id || Merchant Number || DeviceNumber.
  - [x] check Relation Between Merchant and Device (used by Integrity Service).
  - [x] check fro merchant Status (used by Integrity Service).

  - [x] add new Device.
  - [x] get List of Devices.
  - [x] get Device By Id.

### Eureka Service.

- [x] Add Eureka Server and Enable it.

### Api Geteway Service.

- [x] Add Api Geteway Configuration.


### Done ✓
