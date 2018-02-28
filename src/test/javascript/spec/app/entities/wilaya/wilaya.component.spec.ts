/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RimAnnoceTestModule } from '../../../test.module';
import { WilayaComponent } from '../../../../../../main/webapp/app/entities/wilaya/wilaya.component';
import { WilayaService } from '../../../../../../main/webapp/app/entities/wilaya/wilaya.service';
import { Wilaya } from '../../../../../../main/webapp/app/entities/wilaya/wilaya.model';

describe('Component Tests', () => {

    describe('Wilaya Management Component', () => {
        let comp: WilayaComponent;
        let fixture: ComponentFixture<WilayaComponent>;
        let service: WilayaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [RimAnnoceTestModule],
                declarations: [WilayaComponent],
                providers: [
                    WilayaService
                ]
            })
            .overrideTemplate(WilayaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WilayaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WilayaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Wilaya(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.wilayas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
