import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INetflixUser } from 'app/shared/model/netflix-user.model';

type EntityResponseType = HttpResponse<INetflixUser>;
type EntityArrayResponseType = HttpResponse<INetflixUser[]>;

@Injectable({ providedIn: 'root' })
export class NetflixUserService {
  public resourceUrl = SERVER_API_URL + 'api/netflix-users';

  constructor(protected http: HttpClient) {}

  create(netflixUser: INetflixUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(netflixUser);
    return this.http
      .post<INetflixUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(netflixUser: INetflixUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(netflixUser);
    return this.http
      .put<INetflixUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INetflixUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INetflixUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(netflixUser: INetflixUser): INetflixUser {
    const copy: INetflixUser = Object.assign({}, netflixUser, {
      birthDate: netflixUser.birthDate && netflixUser.birthDate.isValid() ? netflixUser.birthDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate ? moment(res.body.birthDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((netflixUser: INetflixUser) => {
        netflixUser.birthDate = netflixUser.birthDate ? moment(netflixUser.birthDate) : undefined;
      });
    }
    return res;
  }
}
