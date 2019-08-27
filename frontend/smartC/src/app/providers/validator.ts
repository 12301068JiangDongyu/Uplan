import {FormBuilder,FormControl,Validators,AbstractControl } from '@angular/forms';


export function usernameValidator(control: FormControl): { [s: string]: boolean } {
    if (!control.value.match(/^[(\u4e00-\u9fa5)0-9a-zA-Z\_\s@]+$/)) {
        return { invalidUsername: true };
    }
}

export function nameValidator(name): boolean{
	let re = /^[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$/;
	return re.test(name);
}

export function numValidator(num): boolean{
	let re = /^[0-9]{1,10}$/;
	return re.test(num);
}

