import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RimAnnoceWilayaModule } from './wilaya/wilaya.module';
import { RimAnnoceMouqataaModule } from './mouqataa/mouqataa.module';
import { RimAnnoceCategorieModule } from './categorie/categorie.module';
import { RimAnnoceAnnonceModule } from './annonce/annonce.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        RimAnnoceWilayaModule,
        RimAnnoceMouqataaModule,
        RimAnnoceCategorieModule,
        RimAnnoceAnnonceModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RimAnnoceEntityModule {}
