import { BaseEntity } from './../../shared';

export class Wilaya implements BaseEntity {
    constructor(
        public id?: number,
        public wilayaName?: string,
    ) {
    }
}
