import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MAINMENU_ROUTES } from './app.router';
import { RouterModule } from '@angular/router';
import { DataTableModule } from "angular2-datatable";
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastModule } from 'ng2-toastr/ng2-toastr';
import { CustomOption } from './custom-option';
import { ToastOptions } from 'ng2-toastr';
import { NgxEchartsModule } from 'ngx-echarts';

import { AppComponent } from './app.component';
import { LoginPage } from './pages/login/login';
import { NavComponent } from './pages/navigation/navigation';
import { SidebarComponent } from './pages/sidebar/sidebar';

import { homePage } from './pages/home/home';
import { deviceMonitorPage } from './pages/device/deviceMonitor/deviceMonitor';
import { deviceInfoPage } from './pages/device/deviceManage/deviceInfo/deviceInfo';
import { assignDevicePage } from './pages/device/deviceManage/assignDevice/assignDevice';
import { assignDeviceClassroomPage } from './pages/device/deviceManage/assignDevice/assignDeviceClassroom/assignDeviceClassroom';

import { videoManagePage } from './pages/video/videoManage/videoManage';
import { videoLivePage } from './pages/video/videoLive/videoLive';
import { userManagePage } from './pages/settings/userManage/userManage';
import { roleManagePage } from './pages/settings/roleManage/roleManage';
import { messageManagePage } from './pages/settings/messageManage/messageManage';
import { buildClassManagePage } from './pages/settings/buildClassManage/buildClassManage';
import { addBuildClassPage } from './pages/settings/buildClassManage/addBuildClass/addBuildClass';

import { StorageService } from './service/storage.service';
import { UserService } from './service/user.service';
import { DeviceService } from './service/device.service';
import { CarService } from './service/car-manage.service';
import { VideoService } from './service/video.service';
import { MessageService } from './service/message.service';
import { RoleService } from './service/role.service';
import { BuildClassService } from './service/buildClass.service';
import { ToastService } from './service/toast.service';
import { CarTypeService } from './service/cartype.service';
import { StatisticsService } from './service/statistics.service';
import { CarRecordService } from './service/carrecord.service';
import { CarApplyInfoService } from './service/car.service';

import {ActivateGuard} from './guard/auth.guard';
import {DeactivateGuard} from './guard/deactivate.guard';

import { Constant } from './common/constant';
import { CarTypeManageComponent } from './pages/info/car-type-manage/car-type-manage.component';
import { CarManageComponent } from './pages/info/car-manage/car-manage.component';
import { CarRecordComponent } from './pages/info/car-record/car-record.component';
import { CarApplyComponent } from './pages/mission/carApply/carApply';
import { UserApplyListComponent  } from './pages/mission/user-apply-list/user-apply-list.component';
import { CarCheckComponent } from './pages/mission/car-check/car-check.component';
import { StatisticsComponent } from './pages/statistics/statistics/statistics.component'

import * as $ from 'jquery';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    NavComponent,
    LoginPage,
    deviceMonitorPage,
    videoManagePage,
    videoLivePage,
    userManagePage,
    roleManagePage,
    buildClassManagePage,
    addBuildClassPage,
    deviceInfoPage,
    assignDevicePage,
    assignDeviceClassroomPage,
    messageManagePage,
    homePage,
    CarTypeManageComponent,
    CarManageComponent,
    CarRecordComponent,
    CarApplyComponent,
    CarCheckComponent,
    StatisticsComponent,
    UserApplyListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    //CONST_ROUTING,
    DataTableModule,
    RouterModule.forRoot(MAINMENU_ROUTES),
    Ng2Bs3ModalModule,
    BrowserAnimationsModule,
    ToastModule.forRoot(),
    NgxEchartsModule
  ],
  providers: [
    {provide: ToastOptions, useClass: CustomOption},
    StorageService,
    UserService,
    DeviceService,
    VideoService,
    MessageService,
    RoleService,
    BuildClassService,
    Constant,
    ToastService,
    CarService,
    ActivateGuard,
    DeactivateGuard,
    CarTypeService,
    StatisticsService,
    CarRecordService,
    CarApplyInfoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
