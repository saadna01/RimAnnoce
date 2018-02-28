import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Wilaya } from './wilaya.model';
import { WilayaService } from './wilaya.service';

@Component({
    selector: 'jhi-wilaya-detail',
    templateUrl: './wilaya-detail.component.html'
})
export class WilayaDetailComponent implements OnInit, OnDestroy {

    wilaya: Wilaya;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private wilayaService: WilayaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWilayas();
    }

    load(id) {
        this.wilayaService.find(id)
            .subscribe((wilayaResponse: HttpResponse<Wilaya>) => {
                this.wilaya = wilayaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWilayas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'wilayaListModification',
            (response) => this.load(this.wilaya.id)
        );
    }
}
