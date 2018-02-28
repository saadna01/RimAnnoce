import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Mouqataa } from './mouqataa.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Mouqataa>;

@Injectable()
export class MouqataaService {

    private resourceUrl =  SERVER_API_URL + 'api/mouqataas';

    constructor(private http: HttpClient) { }

    create(mouqataa: Mouqataa): Observable<EntityResponseType> {
        const copy = this.convert(mouqataa);
        return this.http.post<Mouqataa>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mouqataa: Mouqataa): Observable<EntityResponseType> {
        const copy = this.convert(mouqataa);
        return this.http.put<Mouqataa>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Mouqataa>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Mouqataa[]>> {
        const options = createRequestOption(req);
        return this.http.get<Mouqataa[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Mouqataa[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Mouqataa = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Mouqataa[]>): HttpResponse<Mouqataa[]> {
        const jsonResponse: Mouqataa[] = res.body;
        const body: Mouqataa[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Mouqataa.
     */
    private convertItemFromServer(mouqataa: Mouqataa): Mouqataa {
        const copy: Mouqataa = Object.assign({}, mouqataa);
        return copy;
    }

    /**
     * Convert a Mouqataa to a JSON which can be sent to the server.
     */
    private convert(mouqataa: Mouqataa): Mouqataa {
        const copy: Mouqataa = Object.assign({}, mouqataa);
        return copy;
    }
}
