import { Injectable,ViewContainerRef } from '@angular/core';
import { ToastsManager,Toast } from 'ng2-toastr/ng2-toastr';

@Injectable()

export class ToastService {
  
  dismissTime : number = 5000;

  constructor(){
    
  }
  

  showSuccess(toastr,title,message):void {
    toastr.success(message, title)
      .then((toast: Toast) => {
        setTimeout(() => {
            toastr.dismissToast(toast);
        }, this.dismissTime);
      });
  }

  showError(toastr,title,message) {
    toastr.error(message, title)
      .then((toast: Toast) => {
        setTimeout(() => {
            toastr.dismissToast(toast);
        }, this.dismissTime);
      });
  }

  showWarning(toastr,title,message) {
    toastr.warning(message, title)
      .then((toast: Toast) => {
        setTimeout(() => {
            toastr.dismissToast(toast);
        }, this.dismissTime);
      });
  }

}