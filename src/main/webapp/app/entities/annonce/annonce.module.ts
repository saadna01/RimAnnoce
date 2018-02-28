import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RimAnnoceSharedModule } from '../../shared';
import { RimAnnoceAdminModule } from '../../admin/admin.module';
import {
    AnnonceService,
    AnnoncePopupService,
    AnnonceComponent,
    AnnonceDetailComponent,
    AnnonceDialogComponent,
    AnnoncePopupComponent,
    AnnonceDeletePopupComponent,
    AnnonceDeleteDialogComponent,
    annonceRoute,
    annoncePopupRoute,
    AnnonceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...annonceRoute,
    ...annoncePopupRoute,
];

@NgModule({
    imports: [
        RimAnnoceSharedModule,
        RimAnnoceAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AnnonceComponent,
        AnnonceDetailComponent,
        AnnonceDialogComponent,
        AnnonceDeleteDialogComponent,
        AnnoncePopupComponent,
        AnnonceDeletePopupComponent,
    ],
    entryComponents: [
        AnnonceComponent,
        AnnonceDialogComponent,
        AnnoncePopupComponent,
        AnnonceDeleteDialogComponent,
        AnnonceDeletePopupComponent,
    ],
    providers: [
        AnnonceService,
        AnnoncePopupService,
        AnnonceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RimAnnoceAnnonceModule {}
