import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Mouqataa } from './mouqataa.model';
import { MouqataaService } from './mouqataa.service';

@Component({
    selector: 'jhi-mouqataa-detail',
    templateUrl: './mouqataa-detail.component.html'
})
export class MouqataaDetailComponent implements OnInit, OnDestroy {

    mouqataa: Mouqataa;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mouqataaService: MouqataaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMouqataas();
    }

    load(id) {
        this.mouqataaService.find(id)
            .subscribe((mouqataaResponse: HttpResponse<Mouqataa>) => {
                this.mouqataa = mouqataaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMouqataas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mouqataaListModification',
            (response) => this.load(this.mouqataa.id)
        );
    }
}
