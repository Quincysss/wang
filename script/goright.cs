using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class goright : MonoBehaviour, IPointerDownHandler, IPointerUpHandler, IPointerExitHandler
{
    public move player;
    private bool isDown = false;

    void Update()
    {
        if (isDown)
        {
            player.goright = true;
        }
        else
        {
            player.goright = false;
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
