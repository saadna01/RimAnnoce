import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RimAnnoceSharedModule } from '../../shared';
import {
    WilayaService,
    WilayaPopupService,
    WilayaComponent,
    WilayaDetailComponent,
    WilayaDialogComponent,
    WilayaPopupComponent,
    WilayaDeletePopupComponent,
    WilayaDeleteDialogComponent,
    wilayaRoute,
    wilayaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...wilayaRoute,
    ...wilayaPopupRoute,
];

@NgModule({
    imports: [
        RimAnnoceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WilayaComponent,
        WilayaDetailComponent,
        WilayaDialogComponent,
        WilayaDeleteDialogComponent,
        WilayaPopupComponent,
        WilayaDeletePopupComponent,
    ],
    entryComponents: [
        WilayaComponent,
        WilayaDialogComponent,
        WilayaPopupComponent,
        WilayaDeleteDialogComponent,
        WilayaDeletePopupComponent,
    ],
    providers: [
        WilayaService,
        WilayaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RimAnnoceWilayaModule {}
