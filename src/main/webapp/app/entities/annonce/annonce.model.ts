import { BaseEntity } from './../../shared';

export const enum AnnonceStatus {
    'PENDING',
    'CANCELED',
    'VALIDATED'
}

export class Annonce implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public details?: string,
        public photo1ContentType?: string,
        public photo1?: any,
        public photo2ContentType?: string,
        public photo2?: any,
        public photo3ContentType?: string,
        public photo3?: any,
        public prix?: number,
        public date?: any,
        public status?: AnnonceStatus,
        public mouqataaId?: number,
        public categorieId?: number,
        public userId?: number,
    ) {
    }
}
