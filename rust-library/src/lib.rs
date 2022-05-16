use jni::JNIEnv;
use jni::objects::{JObject, JString};
use jni::sys::jstring;
use libmathcat::{get_version, set_rules_dir, set_mathml, errors_to_string};

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_hello(env: JNIEnv, _obj: JObject, s: JString) -> jstring {
    let input: String = env.get_string(s).expect("Could not get java string").into();
    let output = env.new_string(format!("Hello, {}!", input)).expect("Could not create java string");
    output.into_inner()
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_getVersion(env: JNIEnv, _obj: JObject) -> jstring {
    let output = env.new_string(get_version()).expect("Could not create java string");
    output.into_inner()
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_setRulesDir(env: JNIEnv, _obj: JObject, dir: JString) {
    let dir = env.get_string(dir).expect("Could not get string value of dir").into();
    let _ = set_rules_dir(dir).or_else(|e| env.throw_new("java/lang/RuntimeException", errors_to_string(&e)));
}

#[no_mangle]
pub extern "system" fn Java_onl_mdw_mathkat_MathKat_setMathml(env: JNIEnv, _obj: JObject, mathml_str: JString) -> jstring {
    let mathml_str = env.get_string(mathml_str).expect("Could not get string value of mathml_str").into();
    let canonical_mathml_str = set_mathml(mathml_str).unwrap_or_else(|e| errors_to_string(&e));
    let output = env.new_string(canonical_mathml_str).expect("Could not create java string for output");
    output.into_inner()
}