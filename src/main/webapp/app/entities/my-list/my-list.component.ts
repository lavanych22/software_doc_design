import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMyList } from 'app/shared/model/my-list.model';
import { MyListService } from './my-list.service';
import { MyListDeleteDialogComponent } from './my-list-delete-dialog.component';

@Component({
  selector: 'jhi-my-list',
  templateUrl: './my-list.component.html'
})
export class MyListComponent implements OnInit, OnDestroy {
  myLists?: IMyList[];
  eventSubscriber?: Subscription;

  constructor(protected myListService: MyListService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.myListService.query().subscribe((res: HttpResponse<IMyList[]>) => (this.myLists = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMyLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMyList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMyLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('myListListModification', () => this.loadAll());
  }

  delete(myList: IMyList): void {
    const modalRef = this.modalService.open(MyListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.myList = myList;
  }
}
