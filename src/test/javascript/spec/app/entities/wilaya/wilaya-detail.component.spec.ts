/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { RimAnnoceTestModule } from '../../../test.module';
import { WilayaDetailComponent } from '../../../../../../main/webapp/app/entities/wilaya/wilaya-detail.component';
import { WilayaService } from '../../../../../../main/webapp/app/entities/wilaya/wilaya.service';
import { Wilaya } from '../../../../../../main/webapp/app/entities/wilaya/wilaya.model';

describe('Component Tests', () => {

    describe('Wilaya Management Detail Component', () => {
        let comp: WilayaDetailComponent;
        let fixture: ComponentFixture<WilayaDetailComponent>;
        let service: WilayaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimAnnoceTestModule],
                declarations: [WilayaDetailComponent],
                providers: [
                    WilayaService
                ]
            })
            .overrideTemplate(WilayaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WilayaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WilayaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Wilaya(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.wilaya).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
