import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Wilaya } from './wilaya.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Wilaya>;

@Injectable()
export class WilayaService {

    private resourceUrl =  SERVER_API_URL + 'api/wilayas';

    constructor(private http: HttpClient) { }

    create(wilaya: Wilaya): Observable<EntityResponseType> {
        const copy = this.convert(wilaya);
        return this.http.post<Wilaya>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(wilaya: Wilaya): Observable<EntityResponseType> {
        const copy = this.convert(wilaya);
        return this.http.put<Wilaya>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Wilaya>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Wilaya[]>> {
        const options = createRequestOption(req);
        return this.http.get<Wilaya[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Wilaya[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Wilaya = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Wilaya[]>): HttpResponse<Wilaya[]> {
        const jsonResponse: Wilaya[] = res.body;
        const body: Wilaya[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Wilaya.
     */
    private convertItemFromServer(wilaya: Wilaya): Wilaya {
        const copy: Wilaya = Object.assign({}, wilaya);
        return copy;
    }

    /**
     * Convert a Wilaya to a JSON which can be sent to the server.
     */
    private convert(wilaya: Wilaya): Wilaya {
        const copy: Wilaya = Object.assign({}, wilaya);
        return copy;
    }
}
