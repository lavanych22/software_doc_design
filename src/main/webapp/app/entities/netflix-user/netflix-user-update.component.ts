import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INetflixUser, NetflixUser } from 'app/shared/model/netflix-user.model';
import { NetflixUserService } from './netflix-user.service';
import { IMyList } from 'app/shared/model/my-list.model';
import { MyListService } from 'app/entities/my-list/my-list.service';

@Component({
  selector: 'jhi-netflix-user-update',
  templateUrl: './netflix-user-update.component.html'
})
export class NetflixUserUpdateComponent implements OnInit {
  isSaving = false;
  mylists: IMyList[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(100)]],
    bio: [null, [Validators.maxLength(1000)]],
    password: [null, [Validators.required, Validators.maxLength(50)]],
    birthDate: [],
    category: [],
    myList: []
  });

  constructor(
    protected netflixUserService: NetflixUserService,
    protected myListService: MyListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ netflixUser }) => {
      if (!netflixUser.id) {
        const today = moment().startOf('day');
        netflixUser.birthDate = today;
      }

      this.updateForm(netflixUser);

      this.myListService
        .query({ filter: 'netflixuser-is-null' })
        .pipe(
          map((res: HttpResponse<IMyList[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMyList[]) => {
          if (!netflixUser.myList || !netflixUser.myList.id) {
            this.mylists = resBody;
          } else {
            this.myListService
              .find(netflixUser.myList.id)
              .pipe(
                map((subRes: HttpResponse<IMyList>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMyList[]) => (this.mylists = concatRes));
          }
        });
    });
  }

  updateForm(netflixUser: INetflixUser): void {
    this.editForm.patchValue({
      id: netflixUser.id,
      name: netflixUser.name,
      bio: netflixUser.bio,
      password: netflixUser.password,
      birthDate: netflixUser.birthDate ? netflixUser.birthDate.format(DATE_TIME_FORMAT) : null,
      category: netflixUser.category,
      myList: netflixUser.myList
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const netflixUser = this.createFromForm();
    if (netflixUser.id !== undefined) {
      this.subscribeToSaveResponse(this.netflixUserService.update(netflixUser));
    } else {
      this.subscribeToSaveResponse(this.netflixUserService.create(netflixUser));
    }
  }

  private createFromForm(): INetflixUser {
    return {
      ...new NetflixUser(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      bio: this.editForm.get(['bio'])!.value,
      password: this.editForm.get(['password'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value ? moment(this.editForm.get(['birthDate'])!.value, DATE_TIME_FORMAT) : undefined,
      category: this.editForm.get(['category'])!.value,
      myList: this.editForm.get(['myList'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INetflixUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IMyList): any {
    return item.id;
  }
}
