import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Wilaya } from './wilaya.model';
import { WilayaPopupService } from './wilaya-popup.service';
import { WilayaService } from './wilaya.service';

@Component({
    selector: 'jhi-wilaya-dialog',
    templateUrl: './wilaya-dialog.component.html'
})
export class WilayaDialogComponent implements OnInit {

    wilaya: Wilaya;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private wilayaService: WilayaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.wilaya.id !== undefined) {
            this.subscribeToSaveResponse(
                this.wilayaService.update(this.wilaya));
        } else {
            this.subscribeToSaveResponse(
                this.wilayaService.create(this.wilaya));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Wilaya>>) {
        result.subscribe((res: HttpResponse<Wilaya>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Wilaya) {
        this.eventManager.broadcast({ name: 'wilayaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-wilaya-popup',
    template: ''
})
export class WilayaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wilayaPopupService: WilayaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.wilayaPopupService
                    .open(WilayaDialogComponent as Component, params['id']);
            } else {
                this.wilayaPopupService
                    .open(WilayaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
