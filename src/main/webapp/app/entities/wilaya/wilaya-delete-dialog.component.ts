import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Wilaya } from './wilaya.model';
import { WilayaPopupService } from './wilaya-popup.service';
import { WilayaService } from './wilaya.service';

@Component({
    selector: 'jhi-wilaya-delete-dialog',
    templateUrl: './wilaya-delete-dialog.component.html'
})
export class WilayaDeleteDialogComponent {

    wilaya: Wilaya;

    constructor(
        private wilayaService: WilayaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wilayaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'wilayaListModification',
                content: 'Deleted an wilaya'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wilaya-delete-popup',
    template: ''
})
export class WilayaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private wilayaPopupService: WilayaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.wilayaPopupService
                .open(WilayaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
