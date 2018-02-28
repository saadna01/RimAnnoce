import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Wilaya } from './wilaya.model';
import { WilayaService } from './wilaya.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-wilaya',
    templateUrl: './wilaya.component.html'
})
export class WilayaComponent implements OnInit, OnDestroy {
wilayas: Wilaya[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private wilayaService: WilayaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.wilayaService.query().subscribe(
            (res: HttpResponse<Wilaya[]>) => {
                this.wilayas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInWilayas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Wilaya) {
        return item.id;
    }
    registerChangeInWilayas() {
        this.eventSubscriber = this.eventManager.subscribe('wilayaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
