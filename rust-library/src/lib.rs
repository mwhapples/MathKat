/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */

use jni::JNIEnv;
use jni::objects::{JClass, JObject, JString, JValue};
use jni::sys::{jboolean, jint, JNI_TRUE, jobject, jsize, jstring};
use libmathcat::*;
use libmathcat::errors::Error;

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_getVersion(env: JNIEnv, _obj: JObject) -> jstring {
    env.new_string(get_version()).expect("Could not create java string").into_inner()
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_setRulesDir(env: JNIEnv, _obj: JObject, dir: JString) {
    let dir = env.get_string(dir).expect("Could not get string value of dir").into();
    let _ = set_rules_dir(dir).or_else(|e| env.throw_new("java/lang/RuntimeException", errors_to_string(&e)));
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_setPreference(env: JNIEnv, _obj: JObject, name: JString, value: JString) {
    let name = env.get_string(name).expect("Could not get string for name").into();
    let value = env.get_string(value).expect("Could not get string for value").into();
    let _ = set_preference(name, value).or_else(|e| env.throw_new("java/lang/RuntimeException", errors_to_string(&e)));
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_getPreference(env: JNIEnv, _obj: JObject, name: JString) -> jstring {
    let name = env.get_string(name).expect("Could not get string for name").into();
    let result = get_preference(name);
    get_string_or_throw(env, result)
}
#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_setMathml(env: JNIEnv, _obj: JObject, mathml_str: JString) -> jstring {
    let mathml_str = env.get_string(mathml_str).expect("Could not get string value of mathml_str").into();
    let canonical_mathml_result = set_mathml(mathml_str);
    get_string_or_throw(env, canonical_mathml_result)
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_getBraille(env: JNIEnv, _obj: JObject, navigation_id: JString) -> jstring {
    let navigation_id = env.get_string(navigation_id).expect("Could not get Java String for navigation_id").into();
    let braille_result = get_braille(navigation_id);
    get_string_or_throw(env, braille_result)
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_getSpokenText(env: JNIEnv, _obj: JObject) -> jstring {
    let spoken_result = get_spoken_text();
    get_string_or_throw(env, spoken_result)
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_getOverviewText(env: JNIEnv, _obj: JObject) -> jstring {
    let overview_result = get_overview_text();
    get_string_or_throw(env, overview_result)
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_doNavigateKeypress(env: JNIEnv, _obj: JObject, key: jint, shift_key: jboolean, control_key: jboolean, alt_key: jboolean, meta_key: jboolean) -> jstring {
    let result = do_navigate_keypress(key as usize, shift_key == JNI_TRUE, control_key == JNI_TRUE, alt_key == JNI_TRUE, meta_key == JNI_TRUE);
    get_string_or_throw(env, result)
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_doNavigateCommand(env: JNIEnv, _obj: JObject, command: JString) -> jstring {
    let command = env.get_string(command).expect("Could not get Java String for command").into();
    let result = do_navigate_command(command);
    get_string_or_throw(env, result)
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathcat4j_MathCatImpl_getNavigationMathml(env: JNIEnv, _obj: JObject) -> jobject {
    let result = get_navigation_mathml();
    get_navigation_position_or_throw(env, result)
}

fn get_navigation_position_or_throw(env: JNIEnv, result: Result<(String, usize), Error>) -> jobject {
    let cls_str = "onl/mdw/mathcat4j/NavigationPosition";
    let signature = "(Ljava/lang/String;I)V";
    match result {
        Ok((id, offset)) => {
            let arguments = &[JValue::from(env.new_string(id).expect("Unable to create Java string")), JValue::from(jint::try_from(offset).unwrap())];
            env.new_object(cls_str, signature, arguments).unwrap().into_inner()
        },
        Err(e) => {
            let _ = env.throw_new("java/lang/RuntimeException", errors_to_string(&e));
            JObject::null().into_inner()
        }
    }
}

fn get_string_or_throw(env: JNIEnv, result: Result<String, Error>) -> jstring {
    match result {
        Ok(s) => env.new_string(s).expect("Could not create Java String").into_inner(),
        Err(e) => {
            let _ = env.throw_new("java/lang/RuntimeException", errors_to_string(&e));
            JObject::null().into_inner()
        }
    }
}