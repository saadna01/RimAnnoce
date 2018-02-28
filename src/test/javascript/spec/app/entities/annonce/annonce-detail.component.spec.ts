/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { RimAnnoceTestModule } from '../../../test.module';
import { AnnonceDetailComponent } from '../../../../../../main/webapp/app/entities/annonce/annonce-detail.component';
import { AnnonceService } from '../../../../../../main/webapp/app/entities/annonce/annonce.service';
import { Annonce } from '../../../../../../main/webapp/app/entities/annonce/annonce.model';

describe('Component Tests', () => {

    describe('Annonce Management Detail Component', () => {
        let comp: AnnonceDetailComponent;
        let fixture: ComponentFixture<AnnonceDetailComponent>;
        let service: AnnonceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimAnnoceTestModule],
                declarations: [AnnonceDetailComponent],
                providers: [
                    AnnonceService
                ]
            })
            .overrideTemplate(AnnonceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnonceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnonceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Annonce(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.annonce).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
