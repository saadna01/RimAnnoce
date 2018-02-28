import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MouqataaComponent } from './mouqataa.component';
import { MouqataaDetailComponent } from './mouqataa-detail.component';
import { MouqataaPopupComponent } from './mouqataa-dialog.component';
import { MouqataaDeletePopupComponent } from './mouqataa-delete-dialog.component';

export const mouqataaRoute: Routes = [
    {
        path: 'mouqataa',
        component: MouqataaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.mouqataa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mouqataa/:id',
        component: MouqataaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.mouqataa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mouqataaPopupRoute: Routes = [
    {
        path: 'mouqataa-new',
        component: MouqataaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.mouqataa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mouqataa/:id/edit',
        component: MouqataaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.mouqataa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mouqataa/:id/delete',
        component: MouqataaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.mouqataa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
