import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mouqataa } from './mouqataa.model';
import { MouqataaService } from './mouqataa.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-mouqataa',
    templateUrl: './mouqataa.component.html'
})
export class MouqataaComponent implements OnInit, OnDestroy {
mouqataas: Mouqataa[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mouqataaService: MouqataaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mouqataaService.query().subscribe(
            (res: HttpResponse<Mouqataa[]>) => {
                this.mouqataas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMouqataas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Mouqataa) {
        return item.id;
    }
    registerChangeInMouqataas() {
        this.eventSubscriber = this.eventManager.subscribe('mouqataaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
