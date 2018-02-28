import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RimAnnoceSharedModule } from '../../shared';
import {
    MouqataaService,
    MouqataaPopupService,
    MouqataaComponent,
    MouqataaDetailComponent,
    MouqataaDialogComponent,
    MouqataaPopupComponent,
    MouqataaDeletePopupComponent,
    MouqataaDeleteDialogComponent,
    mouqataaRoute,
    mouqataaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mouqataaRoute,
    ...mouqataaPopupRoute,
];

@NgModule({
    imports: [
        RimAnnoceSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MouqataaComponent,
        MouqataaDetailComponent,
        MouqataaDialogComponent,
        MouqataaDeleteDialogComponent,
        MouqataaPopupComponent,
        MouqataaDeletePopupComponent,
    ],
    entryComponents: [
        MouqataaComponent,
        MouqataaDialogComponent,
        MouqataaPopupComponent,
        MouqataaDeleteDialogComponent,
        MouqataaDeletePopupComponent,
    ],
    providers: [
        MouqataaService,
        MouqataaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RimAnnoceMouqataaModule {}
