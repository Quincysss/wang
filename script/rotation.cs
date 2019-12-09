using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class rotation : MonoBehaviour
{
    float y = 180;
    void Update()
    {
        y += 0.5f;
        transform.localEulerAngles = new Vector3(0, y, 0);    
    }
}
