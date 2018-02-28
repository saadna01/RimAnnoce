import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mouqataa } from './mouqataa.model';
import { MouqataaPopupService } from './mouqataa-popup.service';
import { MouqataaService } from './mouqataa.service';

@Component({
    selector: 'jhi-mouqataa-delete-dialog',
    templateUrl: './mouqataa-delete-dialog.component.html'
})
export class MouqataaDeleteDialogComponent {

    mouqataa: Mouqataa;

    constructor(
        private mouqataaService: MouqataaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mouqataaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mouqataaListModification',
                content: 'Deleted an mouqataa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mouqataa-delete-popup',
    template: ''
})
export class MouqataaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mouqataaPopupService: MouqataaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mouqataaPopupService
                .open(MouqataaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
