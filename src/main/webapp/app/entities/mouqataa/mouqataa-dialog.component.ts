import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mouqataa } from './mouqataa.model';
import { MouqataaPopupService } from './mouqataa-popup.service';
import { MouqataaService } from './mouqataa.service';
import { Wilaya, WilayaService } from '../wilaya';

@Component({
    selector: 'jhi-mouqataa-dialog',
    templateUrl: './mouqataa-dialog.component.html'
})
export class MouqataaDialogComponent implements OnInit {

    mouqataa: Mouqataa;
    isSaving: boolean;

    wilayas: Wilaya[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mouqataaService: MouqataaService,
        private wilayaService: WilayaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.wilayaService.query()
            .subscribe((res: HttpResponse<Wilaya[]>) => { this.wilayas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mouqataa.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mouqataaService.update(this.mouqataa));
        } else {
            this.subscribeToSaveResponse(
                this.mouqataaService.create(this.mouqataa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Mouqataa>>) {
        result.subscribe((res: HttpResponse<Mouqataa>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Mouqataa) {
        this.eventManager.broadcast({ name: 'mouqataaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackWilayaById(index: number, item: Wilaya) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mouqataa-popup',
    template: ''
})
export class MouqataaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mouqataaPopupService: MouqataaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mouqataaPopupService
                    .open(MouqataaDialogComponent as Component, params['id']);
            } else {
                this.mouqataaPopupService
                    .open(MouqataaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
