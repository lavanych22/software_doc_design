import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INetflixUser } from 'app/shared/model/netflix-user.model';
import { NetflixUserService } from './netflix-user.service';
import { NetflixUserDeleteDialogComponent } from './netflix-user-delete-dialog.component';

@Component({
  selector: 'jhi-netflix-user',
  templateUrl: './netflix-user.component.html'
})
export class NetflixUserComponent implements OnInit, OnDestroy {
  netflixUsers?: INetflixUser[];
  eventSubscriber?: Subscription;

  constructor(
    protected netflixUserService: NetflixUserService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.netflixUserService.query().subscribe((res: HttpResponse<INetflixUser[]>) => (this.netflixUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNetflixUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INetflixUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNetflixUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('netflixUserListModification', () => this.loadAll());
  }

  delete(netflixUser: INetflixUser): void {
    const modalRef = this.modalService.open(NetflixUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.netflixUser = netflixUser;
  }
}
