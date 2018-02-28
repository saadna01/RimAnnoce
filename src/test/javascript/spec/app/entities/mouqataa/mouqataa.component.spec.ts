/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RimAnnoceTestModule } from '../../../test.module';
import { MouqataaComponent } from '../../../../../../main/webapp/app/entities/mouqataa/mouqataa.component';
import { MouqataaService } from '../../../../../../main/webapp/app/entities/mouqataa/mouqataa.service';
import { Mouqataa } from '../../../../../../main/webapp/app/entities/mouqataa/mouqataa.model';

describe('Component Tests', () => {

    describe('Mouqataa Management Component', () => {
        let comp: MouqataaComponent;
        let fixture: ComponentFixture<MouqataaComponent>;
        let service: MouqataaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimAnnoceTestModule],
                declarations: [MouqataaComponent],
                providers: [
                    MouqataaService
                ]
            })
            .overrideTemplate(MouqataaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MouqataaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MouqataaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Mouqataa(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mouqataas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
