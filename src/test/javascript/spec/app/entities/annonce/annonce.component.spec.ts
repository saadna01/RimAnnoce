/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RimAnnoceTestModule } from '../../../test.module';
import { AnnonceComponent } from '../../../../../../main/webapp/app/entities/annonce/annonce.component';
import { AnnonceService } from '../../../../../../main/webapp/app/entities/annonce/annonce.service';
import { Annonce } from '../../../../../../main/webapp/app/entities/annonce/annonce.model';

describe('Component Tests', () => {

    describe('Annonce Management Component', () => {
        let comp: AnnonceComponent;
        let fixture: ComponentFixture<AnnonceComponent>;
        let service: AnnonceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimAnnoceTestModule],
                declarations: [AnnonceComponent],
                providers: [
                    AnnonceService
                ]
            })
            .overrideTemplate(AnnonceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnonceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnonceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Annonce(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.annonces[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
