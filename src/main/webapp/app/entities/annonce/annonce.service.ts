import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Annonce } from './annonce.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Annonce>;

@Injectable()
export class AnnonceService {

    private resourceUrl =  SERVER_API_URL + 'api/annonces';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(annonce: Annonce): Observable<EntityResponseType> {
        const copy = this.convert(annonce);
        return this.http.post<Annonce>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(annonce: Annonce): Observable<EntityResponseType> {
        const copy = this.convert(annonce);
        return this.http.put<Annonce>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Annonce>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Annonce[]>> {
        const options = createRequestOption(req);
        return this.http.get<Annonce[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Annonce[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Annonce = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Annonce[]>): HttpResponse<Annonce[]> {
        const jsonResponse: Annonce[] = res.body;
        const body: Annonce[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Annonce.
     */
    private convertItemFromServer(annonce: Annonce): Annonce {
        const copy: Annonce = Object.assign({}, annonce);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(annonce.date);
        return copy;
    }

    /**
     * Convert a Annonce to a JSON which can be sent to the server.
     */
    private convert(annonce: Annonce): Annonce {
        const copy: Annonce = Object.assign({}, annonce);

        copy.date = this.dateUtils.toDate(annonce.date);
        return copy;
    }
}
