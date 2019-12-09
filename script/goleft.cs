using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class goleft : MonoBehaviour,IPointerDownHandler, IPointerUpHandler, IPointerExitHandler
{
    public move player;
    private bool isDown = false;

    void Update()
    {
        if (isDown)
        {
            player.goleft = true;
        }
        else
        {
            player.goleft = false;
        }

    }
    public void OnPointerDown(PointerEventData eventData)
    {
        isDown = true;
    }

    public void OnPointerUp(PointerEventData eventData)
    {
        isDown = false;
    }
    public void OnPointerExit(PointerEventData eventData)
    {
        isDown = false;
    }
}
