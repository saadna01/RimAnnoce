import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Annonce } from './annonce.model';
import { AnnonceService } from './annonce.service';

@Component({
    selector: 'jhi-annonce-detail',
    templateUrl: './annonce-detail.component.html'
})
export class AnnonceDetailComponent implements OnInit, OnDestroy {

    annonce: Annonce;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private annonceService: AnnonceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnnonces();
    }

    load(id) {
        this.annonceService.find(id)
            .subscribe((annonceResponse: HttpResponse<Annonce>) => {
                this.annonce = annonceResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnnonces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'annonceListModification',
            (response) => this.load(this.annonce.id)
        );
    }
}
