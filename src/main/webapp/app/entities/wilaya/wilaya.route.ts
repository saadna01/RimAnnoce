import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { WilayaComponent } from './wilaya.component';
import { WilayaDetailComponent } from './wilaya-detail.component';
import { WilayaPopupComponent } from './wilaya-dialog.component';
import { WilayaDeletePopupComponent } from './wilaya-delete-dialog.component';

export const wilayaRoute: Routes = [
    {
        path: 'wilaya',
        component: WilayaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.wilaya.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'wilaya/:id',
        component: WilayaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.wilaya.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wilayaPopupRoute: Routes = [
    {
        path: 'wilaya-new',
        component: WilayaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.wilaya.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wilaya/:id/edit',
        component: WilayaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.wilaya.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'wilaya/:id/delete',
        component: WilayaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.wilaya.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
