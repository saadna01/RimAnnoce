import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Annonce } from './annonce.model';
import { AnnoncePopupService } from './annonce-popup.service';
import { AnnonceService } from './annonce.service';
import { Mouqataa, MouqataaService } from '../mouqataa';
import { Categorie, CategorieService } from '../categorie';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-annonce-dialog',
    templateUrl: './annonce-dialog.component.html'
})
export class AnnonceDialogComponent implements OnInit {

    annonce: Annonce;
    isSaving: boolean;

    mouqataas: Mouqataa[];

    categories: Categorie[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private annonceService: AnnonceService,
        private mouqataaService: MouqataaService,
        private categorieService: CategorieService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mouqataaService.query()
            .subscribe((res: HttpResponse<Mouqataa[]>) => { this.mouqataas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.categorieService.query()
            .subscribe((res: HttpResponse<Categorie[]>) => { this.categories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.annonce.id !== undefined) {
            this.subscribeToSaveResponse(
                this.annonceService.update(this.annonce));
        } else {
            this.subscribeToSaveResponse(
                this.annonceService.create(this.annonce));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Annonce>>) {
        result.subscribe((res: HttpResponse<Annonce>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Annonce) {
        this.eventManager.broadcast({ name: 'annonceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMouqataaById(index: number, item: Mouqataa) {
        return item.id;
    }

    trackCategorieById(index: number, item: Categorie) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-annonce-popup',
    template: ''
})
export class AnnoncePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private annoncePopupService: AnnoncePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.annoncePopupService
                    .open(AnnonceDialogComponent as Component, params['id']);
            } else {
                this.annoncePopupService
                    .open(AnnonceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
