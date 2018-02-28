import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Annonce } from './annonce.model';
import { AnnoncePopupService } from './annonce-popup.service';
import { AnnonceService } from './annonce.service';

@Component({
    selector: 'jhi-annonce-delete-dialog',
    templateUrl: './annonce-delete-dialog.component.html'
})
export class AnnonceDeleteDialogComponent {

    annonce: Annonce;

    constructor(
        private annonceService: AnnonceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.annonceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'annonceListModification',
                content: 'Deleted an annonce'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-annonce-delete-popup',
    template: ''
})
export class AnnonceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private annoncePopupService: AnnoncePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.annoncePopupService
                .open(AnnonceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
