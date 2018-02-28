import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AnnonceComponent } from './annonce.component';
import { AnnonceDetailComponent } from './annonce-detail.component';
import { AnnoncePopupComponent } from './annonce-dialog.component';
import { AnnonceDeletePopupComponent } from './annonce-delete-dialog.component';

@Injectable()
export class AnnonceResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const annonceRoute: Routes = [
    {
        path: 'annonce',
        component: AnnonceComponent,
        resolve: {
            'pagingParams': AnnonceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.annonce.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'annonce/:id',
        component: AnnonceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.annonce.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const annoncePopupRoute: Routes = [
    {
        path: 'annonce-new',
        component: AnnoncePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.annonce.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'annonce/:id/edit',
        component: AnnoncePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.annonce.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'annonce/:id/delete',
        component: AnnonceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rimAnnoceApp.annonce.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
