import { BaseEntity } from './../../shared';

export class Mouqataa implements BaseEntity {
    constructor(
        public id?: number,
        public mouqataaName?: string,
        public wilayaId?: number,
    ) {
    }
}
