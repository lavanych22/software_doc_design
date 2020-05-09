import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NetflixTestModule } from '../../../test.module';
import { NetflixUserComponent } from 'app/entities/netflix-user/netflix-user.component';
import { NetflixUserService } from 'app/entities/netflix-user/netflix-user.service';
import { NetflixUser } from 'app/shared/model/netflix-user.model';

describe('Component Tests', () => {
  describe('NetflixUser Management Component', () => {
    let comp: NetflixUserComponent;
    let fixture: ComponentFixture<NetflixUserComponent>;
    let service: NetflixUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NetflixTestModule],
        declarations: [NetflixUserComponent]
      })
        .overrideTemplate(NetflixUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NetflixUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NetflixUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NetflixUser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.netflixUsers && comp.netflixUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
