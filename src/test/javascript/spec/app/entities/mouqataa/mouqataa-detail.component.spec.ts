/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { RimAnnoceTestModule } from '../../../test.module';
import { MouqataaDetailComponent } from '../../../../../../main/webapp/app/entities/mouqataa/mouqataa-detail.component';
import { MouqataaService } from '../../../../../../main/webapp/app/entities/mouqataa/mouqataa.service';
import { Mouqataa } from '../../../../../../main/webapp/app/entities/mouqataa/mouqataa.model';

describe('Component Tests', () => {

    describe('Mouqataa Management Detail Component', () => {
        let comp: MouqataaDetailComponent;
        let fixture: ComponentFixture<MouqataaDetailComponent>;
        let service: MouqataaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimAnnoceTestModule],
                declarations: [MouqataaDetailComponent],
                providers: [
                    MouqataaService
                ]
            })
            .overrideTemplate(MouqataaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MouqataaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MouqataaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Mouqataa(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mouqataa).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
